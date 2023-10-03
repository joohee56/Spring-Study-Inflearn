package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 파일 한개 저장
     */
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);       //고유한 파일 이름 생성
        multipartFile.transferTo(new File(getFullPath(storeFileName)));     //저장
        return new UploadFile(originalFilename, storeFileName);
    }

    /**
     * 파일 여러개 저장
     */
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for(MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }

        return storeFileResult;
    }

    /**
     *  고유한 파일 이름 생성
     */
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);  //확장자 추출
        String uuid = UUID.randomUUID().toString(); //고유한 파일 이름
        return uuid + "." + ext;    //고유한 파일 이름.확장자
    }

    /**
     * 확장자 추출
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);     //확장자 반환
    }
}
