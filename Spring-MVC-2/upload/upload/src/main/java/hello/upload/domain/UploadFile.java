package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;  //클라이언트가 전송한 파일명
    private String storeFileName;   //실제 저장된 파일명 (파일명 중복 제거)

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
