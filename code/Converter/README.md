###转化器核心类库(说明调用API)

Converter类负责将图片处理为字符画，并保存为图片，需要导入JavaFX依赖库；  

####依赖环境
JavaFX版本：JavaFX8  
Java版本：Java8

####外部调方式
>//创建Converter对象，参数（源图片路径 String,打印字符 char，字符大小 int, 是否启用背景 boolean ）
Converter converter = Converter.convertWithChar(srcPath,paintChar ,fontSize,backgroundColor));  
//保存为图片文件（png格式），参数（保存文件路径）；
converter.save(savePath);  
//获取绘制图像的视图节点；
VBox pane = converter.getPane();  


####TODO
* 读取文件文本素材，使用文件文本作为打印字符字符集；  
* 增加字符转化进度监听线程，并提供外部调用接口；  
* 改用多线程重构字符转化模块，提高转化效率；  
