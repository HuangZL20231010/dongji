package com.hzl.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface IOssService {

    String upload(MultipartFile file);

}
