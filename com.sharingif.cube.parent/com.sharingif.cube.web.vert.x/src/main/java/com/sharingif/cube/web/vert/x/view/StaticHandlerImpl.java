//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sharingif.cube.web.vert.x.view;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.VertxException;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.impl.MimeMapping;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.impl.LRUCache;
import io.vertx.ext.web.impl.Utils;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.NoSuchFileException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticHandlerImpl implements StaticHandler {
    private static final Logger log = LoggerFactory.getLogger(StaticHandlerImpl.class);
    private final DateFormat dateTimeFormatter = Utils.createRFC1123DateTimeFormatter();
    private Map<String, StaticHandlerImpl.CacheEntry> propsCache;
    private String webRoot = "webroot";
    private long maxAgeSeconds = 86400L;
    private boolean directoryListing = false;
    private String directoryTemplateResource = "vertx-web-directory.html";
    private String directoryTemplate;
    private boolean includeHidden = true;
    private boolean filesReadOnly = true;
    private boolean cachingEnabled = true;
    private long cacheEntryTimeout = 30000L;
    private String indexPage = "/index.html";
    private int maxCacheSize = 10000;
    private boolean rangeSupport = true;
    private boolean allowRootFileSystemAccess = false;
    private boolean sendVaryHeader = true;
    private String defaultContentEncoding = Charset.defaultCharset().name();
    private static int NUM_SERVES_TUNING_FS_ACCESS = 1000;
    private boolean alwaysAsyncFS = false;
    private long maxAvgServeTimeNanoSeconds = 1000000L;
    private boolean tuning = true;
    private long totalTime;
    private long numServesBlocking;
    private boolean useAsyncFS;
    private long nextAvgCheck;
    private final ClassLoader classLoader;
    private static final Pattern RANGE = Pattern.compile("^bytes=(\\d+)-(\\d*)$");

    public StaticHandlerImpl(String root, ClassLoader classLoader) {
        this.nextAvgCheck = (long)NUM_SERVES_TUNING_FS_ACCESS;
        this.classLoader = classLoader;
        this.setRoot(root);
    }

    public StaticHandlerImpl() {
        this.nextAvgCheck = (long)NUM_SERVES_TUNING_FS_ACCESS;
        this.classLoader = null;
    }

    private String directoryTemplate(Vertx vertx) {
        if(this.directoryTemplate == null) {
            this.directoryTemplate = Utils.readFileToString(vertx, this.directoryTemplateResource);
        }

        return this.directoryTemplate;
    }

    private void writeCacheHeaders(HttpServerRequest request, FileProps props) {
        MultiMap headers = request.response().headers();
        if(this.cachingEnabled) {
            headers.set("cache-control", "public, max-age=" + this.maxAgeSeconds);
            headers.set("last-modified", this.dateTimeFormatter.format(Long.valueOf(props.lastModifiedTime())));
            if(this.sendVaryHeader && request.headers().contains("accept-encoding")) {
                headers.set("vary", "accept-encoding");
            }
        }

        headers.set("date", this.dateTimeFormatter.format(new Date()));
    }

    public void handle(RoutingContext context) {
        HttpServerRequest request = context.request();
        if(request.method() != HttpMethod.GET && request.method() != HttpMethod.HEAD) {
            if(log.isTraceEnabled()) {
                log.trace("Not GET or HEAD so ignoring request");
            }

            context.next();
        } else {
            String path = Utils.removeDots(Utils.urlDecode(context.normalisedPath(), false));
            if(path == null) {
                log.warn("Invalid path: " + context.request().path() + " so returning 404");
                context.fail(HttpResponseStatus.NOT_FOUND.code());
                return;
            }

            if(!this.directoryListing && "/".equals(path)) {
                path = this.indexPage;
            }

            this.sendStatic(context, path);
        }

    }

    private void sendStatic(RoutingContext context, String path) {
        String file = null;
        if(!this.includeHidden) {
            file = this.getFile(path, context);
            int idx = file.lastIndexOf(47);
            String name = file.substring(idx + 1);
            if(name.length() > 0 && name.charAt(0) == 46) {
                context.fail(HttpResponseStatus.NOT_FOUND.code());
                return;
            }
        }

        if(this.cachingEnabled) {
            StaticHandlerImpl.CacheEntry entry = (StaticHandlerImpl.CacheEntry)this.propsCache().get(path);
            if(entry != null) {
                HttpServerRequest request = context.request();
                if((this.filesReadOnly || !entry.isOutOfDate()) && entry.shouldUseCached(request)) {
                    context.response().setStatusCode(HttpResponseStatus.NOT_MODIFIED.code()).end();
                    return;
                }
            }
        }

        if(file == null) {
            file = this.getFile(path, context);
        }

        this.getFileProps(context, file, (res) -> {
            if(res.succeeded()) {
                FileProps fprops = (FileProps)res.result();
                if(fprops == null) {
                    context.fail(HttpResponseStatus.NOT_FOUND.code());
                } else if(fprops.isDirectory()) {
                    this.sendDirectory(context, path, file);
                } else {
                    this.propsCache().put(path, new StaticHandlerImpl.CacheEntry(fprops, System.currentTimeMillis()));
                    this.sendFile(context, file, fprops);
                }
            } else if(!(res.cause() instanceof NoSuchFileException) && (res.cause().getCause() == null || !(res.cause().getCause() instanceof NoSuchFileException))) {
                context.fail(res.cause());
            } else {
                context.fail(HttpResponseStatus.NOT_FOUND.code());
            }

        });
    }

    private void sendDirectory(RoutingContext context, String path, String file) {
        if(this.directoryListing) {
            this.sendDirectoryListing(file, context);
        } else if(this.indexPage != null) {
            String indexPath;
            if(path.endsWith("/") && this.indexPage.startsWith("/")) {
                indexPath = path + this.indexPage.substring(1);
            } else if(!path.endsWith("/") && !this.indexPage.startsWith("/")) {
                indexPath = path + "/" + this.indexPage.substring(1);
            } else {
                indexPath = path + this.indexPage;
            }

            this.sendStatic(context, indexPath);
        } else {
            context.fail(HttpResponseStatus.FORBIDDEN.code());
        }

    }

    private <T> T wrapInTCCLSwitch(Callable<T> callable) {
        try {
            if(this.classLoader == null) {
                return callable.call();
            } else {
                ClassLoader original = Thread.currentThread().getContextClassLoader();

                Object var3;
                try {
                    Thread.currentThread().setContextClassLoader(this.classLoader);
                    var3 = callable.call();
                } finally {
                    Thread.currentThread().setContextClassLoader(original);
                }

                return var3;
            }
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        }
    }

    private synchronized void getFileProps(RoutingContext context, String file, Handler<AsyncResult<FileProps>> resultHandler) {
        FileSystem fs = context.vertx().fileSystem();
        if(!this.alwaysAsyncFS && !this.useAsyncFS) {
            long start = 0L;
            if(this.tuning) {
                start = System.nanoTime();
            }

            try {
                FileProps props = (FileProps)this.wrapInTCCLSwitch(() -> {
                    return fs.propsBlocking(file);
                });
                if(this.tuning) {
                    long end = System.nanoTime();
                    long dur = end - start;
                    this.totalTime += dur;
                    ++this.numServesBlocking;
                    if(this.numServesBlocking == 9223372036854775807L) {
                        this.resetTuning();
                    } else if(this.numServesBlocking == this.nextAvgCheck) {
                        double avg = (double)this.totalTime / (double)this.numServesBlocking;
                        if(avg > (double)this.maxAvgServeTimeNanoSeconds) {
                            this.useAsyncFS = true;
                            log.info("Switching to async file system access in static file server as fs access is slow! (Average access time of " + avg + " ns)");
                            this.tuning = false;
                        }

                        this.nextAvgCheck += (long)NUM_SERVES_TUNING_FS_ACCESS;
                    }
                }

                resultHandler.handle(Future.succeededFuture(props));
            } catch (RuntimeException var14) {
                resultHandler.handle(Future.failedFuture(var14.getCause()));
            }
        } else {
            this.wrapInTCCLSwitch(() -> {
                return fs.props(file, resultHandler);
            });
        }

    }

    private void resetTuning() {
        this.nextAvgCheck = (long)NUM_SERVES_TUNING_FS_ACCESS;
        this.totalTime = 0L;
        this.numServesBlocking = 0L;
    }

    private void sendFile(RoutingContext context, String file, FileProps fileProps) {
        HttpServerRequest request = context.request();
        Long offset = null;
        Long end = null;
        MultiMap headers = null;
        if(this.rangeSupport) {
            String range = request.getHeader("Range");
            end = Long.valueOf(fileProps.size() - 1L);
            if(range != null) {
                Matcher m = RANGE.matcher(range);
                if(m.matches()) {
                    try {
                        String part = m.group(1);
                        offset = Long.valueOf(Long.parseLong(part));
                        if(offset.longValue() < 0L || offset.longValue() >= fileProps.size()) {
                            throw new IndexOutOfBoundsException();
                        }

                        part = m.group(2);
                        if(part != null && part.length() > 0) {
                            end = Long.valueOf(Math.min(end.longValue(), Long.parseLong(part)));
                            if(end.longValue() < offset.longValue()) {
                                throw new IndexOutOfBoundsException();
                            }
                        }
                    } catch (IndexOutOfBoundsException | NumberFormatException var11) {
                        context.response().putHeader("Content-Range", "bytes */" + fileProps.size());
                        context.fail(HttpResponseStatus.REQUESTED_RANGE_NOT_SATISFIABLE.code());
                        return;
                    }
                }
            }

            headers = request.response().headers();
            headers.set("Accept-Ranges", "bytes");
            headers.set("Content-Length", Long.toString(end.longValue() + 1L - (offset == null?0L:offset.longValue())));
        }

        this.writeCacheHeaders(request, fileProps);
        if(request.method() == HttpMethod.HEAD) {
            request.response().end();
        } else if(this.rangeSupport && offset != null) {
            headers.set("Content-Range", "bytes " + offset + "-" + end + "/" + fileProps.size());
            request.response().setStatusCode(HttpResponseStatus.PARTIAL_CONTENT.code());
            this.wrapInTCCLSwitch(() -> {
                String contentType = MimeMapping.getMimeTypeForFilename(file);
                if(contentType != null) {
                    if(contentType.startsWith("text")) {
                        request.response().putHeader("Content-Type", contentType + ";charset=" + this.defaultContentEncoding);
                    } else {
                        request.response().putHeader("Content-Type", contentType);
                    }
                }

                return request.response().sendFile(file, offset.longValue(), end.longValue() + 1L, (res2) -> {
                    if(res2.failed()) {
                        context.fail(res2.cause());
                    }

                });
            });
        } else {
            this.wrapInTCCLSwitch(() -> {
                String contentType = MimeMapping.getMimeTypeForFilename(file);
                if(contentType != null) {
                    if(contentType.startsWith("text")) {
                        request.response().putHeader("Content-Type", contentType + ";charset=" + this.defaultContentEncoding);
                    } else {
                        request.response().putHeader("Content-Type", contentType);
                    }
                }

                return request.response().sendFile(file, (res2) -> {
                    if(res2.failed()) {
                        context.fail(res2.cause());
                    }

                });
            });
        }

    }

    public StaticHandler setAllowRootFileSystemAccess(boolean allowRootFileSystemAccess) {
        this.allowRootFileSystemAccess = allowRootFileSystemAccess;
        return this;
    }

    public StaticHandler setWebRoot(String webRoot) {
        this.setRoot(webRoot);
        return this;
    }

    public StaticHandler setFilesReadOnly(boolean readOnly) {
        this.filesReadOnly = readOnly;
        return this;
    }

    public StaticHandler setMaxAgeSeconds(long maxAgeSeconds) {
        if(maxAgeSeconds < 0L) {
            throw new IllegalArgumentException("timeout must be >= 0");
        } else {
            this.maxAgeSeconds = maxAgeSeconds;
            return this;
        }
    }

    public StaticHandler setMaxCacheSize(int maxCacheSize) {
        if(maxCacheSize < 1) {
            throw new IllegalArgumentException("maxCacheSize must be >= 1");
        } else {
            this.maxCacheSize = maxCacheSize;
            return this;
        }
    }

    public StaticHandler setCachingEnabled(boolean enabled) {
        this.cachingEnabled = enabled;
        return this;
    }

    public StaticHandler setDirectoryListing(boolean directoryListing) {
        this.directoryListing = directoryListing;
        return this;
    }

    public StaticHandler setDirectoryTemplate(String directoryTemplate) {
        this.directoryTemplateResource = directoryTemplate;
        this.directoryTemplate = null;
        return this;
    }

    public StaticHandler setEnableRangeSupport(boolean enableRangeSupport) {
        this.rangeSupport = enableRangeSupport;
        return this;
    }

    public StaticHandler setIncludeHidden(boolean includeHidden) {
        this.includeHidden = includeHidden;
        return this;
    }

    public StaticHandler setCacheEntryTimeout(long timeout) {
        if(timeout < 1L) {
            throw new IllegalArgumentException("timeout must be >= 1");
        } else {
            this.cacheEntryTimeout = timeout;
            return this;
        }
    }

    public StaticHandler setIndexPage(String indexPage) {
        Objects.requireNonNull(indexPage);
        if(!indexPage.startsWith("/")) {
            indexPage = "/" + indexPage;
        }

        this.indexPage = indexPage;
        return this;
    }

    public StaticHandler setAlwaysAsyncFS(boolean alwaysAsyncFS) {
        this.alwaysAsyncFS = alwaysAsyncFS;
        return this;
    }

    public synchronized StaticHandler setEnableFSTuning(boolean enableFSTuning) {
        this.tuning = enableFSTuning;
        if(!this.tuning) {
            this.resetTuning();
        }

        return this;
    }

    public StaticHandler setMaxAvgServeTimeNs(long maxAvgServeTimeNanoSeconds) {
        this.maxAvgServeTimeNanoSeconds = maxAvgServeTimeNanoSeconds;
        return this;
    }

    public StaticHandler setSendVaryHeader(boolean sendVaryHeader) {
        this.sendVaryHeader = sendVaryHeader;
        return this;
    }

    public StaticHandler setDefaultContentEncoding(String contentEncoding) {
        this.defaultContentEncoding = contentEncoding;
        return this;
    }

    private Map<String, StaticHandlerImpl.CacheEntry> propsCache() {
        if(this.propsCache == null) {
            this.propsCache = new LRUCache(this.maxCacheSize);
        }

        return this.propsCache;
    }

    private Date parseDate(String header) {
        try {
            return this.dateTimeFormatter.parse(header);
        } catch (ParseException var3) {
            throw new VertxException(var3);
        }
    }

    private String getFile(String path, RoutingContext context) {
        String file = this.webRoot + Utils.pathOffset(path, context);
        if(log.isTraceEnabled()) {
            log.trace("File to serve is " + file);
        }

        return file;
    }

    private void setRoot(String webRoot) {
        Objects.requireNonNull(webRoot);
        if(!this.allowRootFileSystemAccess) {
            File[] var2 = File.listRoots();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                File root = var2[var4];
                if(webRoot.startsWith(root.getAbsolutePath())) {
                    throw new IllegalArgumentException("root cannot start with '" + root.getAbsolutePath() + "'");
                }
            }
        }

        this.webRoot = webRoot;
    }

    private void sendDirectoryListing(String dir, RoutingContext context) {
        FileSystem fileSystem = context.vertx().fileSystem();
        HttpServerRequest request = context.request();
        fileSystem.readDir(dir, (asyncResult) -> {
            if(asyncResult.failed()) {
                context.fail(asyncResult.cause());
            } else {
                String accept = request.headers().get("accept");
                if(accept == null) {
                    accept = "text/plain";
                }

                String normalizedDir;
                if(accept.contains("html")) {
                    normalizedDir = context.normalisedPath();
                    if(!normalizedDir.endsWith("/")) {
                        normalizedDir = normalizedDir + "/";
                    }

                    StringBuilder files = new StringBuilder("<ul id=\"files\">");
                    List<String> list = (List)asyncResult.result();
                    Collections.sort(list);
                    Iterator var9 = list.iterator();

                    while(true) {
                        String file;
                        do {
                            String parent;
                            if(!var9.hasNext()) {
                                files.append("</ul>");
                                int slashPos = 0;

                                for(int i = normalizedDir.length() - 2; i > 0; --i) {
                                    if(normalizedDir.charAt(i) == 47) {
                                        slashPos = i;
                                        break;
                                    }
                                }

                                parent = "<a href=\"" + normalizedDir.substring(0, slashPos + 1) + "\">..</a>";
                                request.response().putHeader("content-type", "text/html");
                                request.response().end(this.directoryTemplate(context.vertx()).replace("{directory}", normalizedDir).replace("{parent}", parent).replace("{files}", files.toString()));
                                return;
                            }

                            parent = (String)var9.next();
                            file = parent.substring(parent.lastIndexOf(File.separatorChar) + 1);
                        } while(!this.includeHidden && file.charAt(0) == 46);

                        files.append("<li><a href=\"");
                        files.append(normalizedDir);
                        files.append(file);
                        files.append("\" title=\"");
                        files.append(file);
                        files.append("\">");
                        files.append(file);
                        files.append("</a></li>");
                    }
                } else {
                    Iterator var13;
                    String s;
                    if(accept.contains("json")) {
                        JsonArray json = new JsonArray();
                        var13 = ((List)asyncResult.result()).iterator();

                        while(true) {
                            do {
                                if(!var13.hasNext()) {
                                    request.response().putHeader("content-type", "application/json");
                                    request.response().end(json.encode());
                                    return;
                                }

                                s = (String)var13.next();
                                normalizedDir = s.substring(s.lastIndexOf(File.separatorChar) + 1);
                            } while(!this.includeHidden && normalizedDir.charAt(0) == 46);

                            json.add(normalizedDir);
                        }
                    } else {
                        StringBuilder buffer = new StringBuilder();
                        var13 = ((List)asyncResult.result()).iterator();

                        while(true) {
                            do {
                                if(!var13.hasNext()) {
                                    request.response().putHeader("content-type", "text/plain");
                                    request.response().end(buffer.toString());
                                    return;
                                }

                                s = (String)var13.next();
                                normalizedDir = s.substring(s.lastIndexOf(File.separatorChar) + 1);
                            } while(!this.includeHidden && normalizedDir.charAt(0) == 46);

                            buffer.append(normalizedDir);
                            buffer.append('\n');
                        }
                    }
                }
            }
        });
    }

    private final class CacheEntry {
        final FileProps props;
        long createDate;

        private CacheEntry(FileProps props, long createDate) {
            this.props = props;
            this.createDate = createDate;
        }

        boolean shouldUseCached(HttpServerRequest request) {
            String ifModifiedSince = request.headers().get("if-modified-since");
            if(ifModifiedSince == null) {
                return false;
            } else {
                Date ifModifiedSinceDate = StaticHandlerImpl.this.parseDate(ifModifiedSince);
                boolean modifiedSince = Utils.secondsFactor(this.props.lastModifiedTime()) > ifModifiedSinceDate.getTime();
                return !modifiedSince;
            }
        }

        boolean isOutOfDate() {
            return System.currentTimeMillis() - this.createDate > StaticHandlerImpl.this.cacheEntryTimeout;
        }
    }
}
