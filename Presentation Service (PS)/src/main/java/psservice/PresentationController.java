//package psservice;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//@RequestMapping("/presentation")
//public class PresentationController {
//
//    @GetMapping("/csv")
//    public ResponseEntity<byte[]> getCSVFile() {
//        try {
//            Path filePath = Paths.get(CSV_FILE_PATH);
//            byte[] csvData = Files.readAllBytes(filePath);
//            return ResponseEntity.ok()
//                    .header("Content-Disposition", "attachment; filename=data.csv")
//                    .body(csvData);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}
