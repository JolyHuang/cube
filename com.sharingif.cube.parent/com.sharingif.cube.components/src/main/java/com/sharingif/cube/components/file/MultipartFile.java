package com.sharingif.cube.components.file;

/**
 * 文件信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/6/29 下午5:56
 */
public class MultipartFile {

    private Long size;
    private String fileName;

    public MultipartFile(Long size, String fileName) {
        this.size = size;
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * 获取文件后缀
     * @return
     */
    protected String getFileNameSuffix(){
        int i = fileName.lastIndexOf('.');
        return fileName.substring(i + 1);
    }

}
