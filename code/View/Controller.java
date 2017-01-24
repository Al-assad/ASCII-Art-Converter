package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Converter.Converter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by Intellij IDEA
 * Author: Al-assad
 * E-mail: yulinying@1994.com
 * Github: https://github.com/Al-assad
 * Date: 2017/1/24 12:43
 * Description:
 */
public class Controller {

    @FXML Label stateLB;
    @FXML Button chooseImageBT;
    @FXML Button createBT;
    @FXML Button saveBT;
    @FXML TextField paintCharTF;
    @FXML TextField fontSizeTF;
    @FXML CheckBox useBackgroundCB;
    @FXML ImageView imageView;
    @FXML Text welcomeText;

    private Converter converter;
    private String srcPath = "";
    private char paintChar;
    private String savePath;
    private int fontSize;
    private String tempSavePath = "tempSave.png";

    private Stage stage;

    public void initialize(){

    }
    public void start(Stage stage){
        this.stage = stage;
        chooseImageBT.setOnAction((ActionEvent e)->{
            File file = new FileChooser().showOpenDialog(stage);
            if(file != null){
                welcomeText.setVisible(false);   //隐藏欢迎标签
                srcPath = file.getAbsolutePath();
                imageView.setImage(new Image("file:"+srcPath));

            }else{
                stateLB.setText("Error src image path");
            }
        });
        createBT.setOnMousePressed(event ->{
            stateLB.setText("Converting......Please wait a minute");
            }
        );
        createBT.setOnAction((ActionEvent e)->{
            //check Input
            if(!srcPath.isEmpty() && !paintCharTF.getText().trim().isEmpty()&& !fontSizeTF.getText().trim().isEmpty()){
                if(!fontSizeTF.getText().trim().matches("[0-9]+") || paintCharTF.getText().trim().charAt(0) == ' ') {
                    stateLB.setText("Please complete the legal setting");
                    return ;
                }
                paintChar =paintCharTF.getText().trim().charAt(0);
                fontSize = Integer.parseInt(fontSizeTF.getText().trim());
                if(fontSize==0){
                    stateLB.setText("Please complete the legal setting");
                    return ;
                }

                converter = Converter.convertWithChar(srcPath,paintChar,fontSize,useBackgroundCB.isSelected());
                converter.save(tempSavePath);

                stateLB.setText("converting completed");
                imageView.setImage(new Image("file:"+tempSavePath));

            }else{
                stateLB.setText("Error setting,please complete the setting");
                return;
            }

        });
        saveBT.setOnAction((ActionEvent e)->{
            FileChooser  chooser = new FileChooser();
            chooser.setInitialFileName("ascii art "+new SimpleDateFormat("hhmmss").format(new Date()));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".png","*.png"));

            savePath = chooser.showSaveDialog(stage).getAbsolutePath();
            if(!savePath.isEmpty()){
                copyFile(tempSavePath,savePath);
                stateLB.setText("aleardy save");


                //自动打开保存文件
                try {
                    Desktop.getDesktop().open(new File(savePath));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

    });


    }
    //复制文件
    private void copyFile(String src,String target){
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(src)));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(target)));
            while(in.available() != 0){
                out.write(in.read());
            }
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
