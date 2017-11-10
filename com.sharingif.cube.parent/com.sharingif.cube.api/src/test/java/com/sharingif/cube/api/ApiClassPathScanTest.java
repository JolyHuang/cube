package com.sharingif.cube.api;

import java.util.List;

/**
 * ApiClassPathScanTest
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/2 下午2:33
 */

public class ApiClassPathScanTest {

    public static void main(String[] args) {
        ApiClassPathScan apiClassPathScan = new ApiClassPathScan("com.sharingif.cube.api");
        List<Class> classList = apiClassPathScan.findCandidateComponents();
        System.out.println(classList);

    }

}
