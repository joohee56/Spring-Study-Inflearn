package hello.upload.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemForm {
    private Long itemId;
    private String itemName;
    private List<MultipartFile> imageFiles;     //이미지 여러 장 전송
    private MultipartFile attachFile;           //첨부파일 한 개 전송
}
