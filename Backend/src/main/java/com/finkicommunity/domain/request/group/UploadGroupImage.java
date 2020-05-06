package com.finkicommunity.domain.request.group;

import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
public class UploadGroupImage {
    public MultipartFile uploadImageData;
    public String groupCode;
}
