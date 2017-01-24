# ASCII-Art-Converter  
##字符画转化器

###依赖环境  
* Java8  
* JavaFX8()

###简述  
核心转化类：Converter.Converter  
视图和控件逻辑处理：View  
打包为jar的工程：ASCII Art Converter.jar  
使用指定的字符替换图片中单位区域的像素点，可自定义字符大小和是否使用全图提取背景色；

###样张 
<img src="sample/捕获.PNG" width="500"><br/>
<img src="sample/sample2.jpg" width="200">
<img src="sample/sample1-1.png" width="250">
<img src="sample/sample1-2.png" width="250">
<img src="sample/sample1-3.png" width="250">

###TODO
* 读取文件文本素材，使用文件文本作为打印字符字符集；  
* 增加字符转化进度监听线程，并提供外部调用接口；  
* 改用多线程重构字符转化模块，提高转化效率；  
