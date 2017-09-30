/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navigator;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import unity.FileDataManager;

public class Vessel extends Navigator {  //水上航行器,具体的类，根据type变量分辨类型
    
    //船舶增加的属性
    private String imoNumber = null;
    private String callNumber = null;
    private String expTime = null;
    
    /**********************-----初始化本类-----*************************/
    //用标志区分航行器还是船舶，比分开写好操作--------------------------3种缺省构造函数
    public Vessel(char type){  //根据输入类型  创建  默认属性的航行器或者船舶
        super(type);
        if (type == 'b') {  //如果是船舶类型，需要加入额外的默认属性
            this.imoNumber = "default";
            this.callNumber = "default";
            this.expTime = "null";
        }
        
        initialVessel();
        clickAction();
    }
    
    public Vessel(String idNumber, String name, int navLength, int beam, char type){
        super(idNumber, name, navLength, beam, type);
        if (type == 'b') {
            this.imoNumber = "default";
            this.callNumber = "default";
            this.expTime = "null";
        }
        
        initialVessel();
        clickAction();
    }
    //创建船舶对象不需要类型  --> 船舶类型是b
    public Vessel(String idNumber, String name, int navLength, int beam,
            String imoNumber, String callNumber, String expTime){
        super(idNumber, name, navLength, beam, 'b');  //如果不写类型，默认是航行器类型，无人艇
        this.imoNumber = imoNumber;
        this.callNumber = callNumber;
        this.expTime = expTime;
        
        initialVessel();
        clickAction();
    }
    private void initialVessel(){  //父类已经初始化了组件的信息，这里只需要初始化其他特殊的信息就行
        //this.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("pic\\Ship.png"))));
    }
    
    private void clickAction(){  //点击当前对象后所做的动作
        this.setOnMouseClicked( new EventHandler<MouseEvent>(){  //中键存储数据
            @Override
            public void handle(MouseEvent event){  //这里只是测试转向的功能，这个功能需要自动完成
                if (event.getButton()==MouseButton.SECONDARY) {  //右键输出本航行器信息
                    //Vessel.this.setRudder(Vessel.this.getRudderAngle() + 2);
                    System.out.println( Vessel.this.getName()
                            +"\nposition-->x :    "+Vessel.this.getPosition().getX()+"    y :     "+Vessel.this.getPosition().getY()
                            +"\nspeed  :   "+Vessel.this.getSpeed()
                            + "\nrudder : "+Vessel.this.getRudderAngle()
                    );
                }
                else if(event.getButton()==MouseButton.PRIMARY){  //左舵-2， 读取本航行器存储的信息
                    //Vessel.this.setRudder(Vessel.this.getRudderAngle()-2);
                    ArrayList<DynInfo> dyninfos = (ArrayList<DynInfo>) FileDataManager.readDyninfos(Vessel.this.getIdNumber());
                    if (dyninfos == null) {
                        System.out.println("no data!!");
                        return;
                    }
                    for (DynInfo info : dyninfos) {
                        System.out.println(Vessel.this.getIdNumber() + "output==> " + info.toString() + "\n");
                    }
                }
                else if(event.getButton() == MouseButton.MIDDLE){  //中键， 对本航行器的航行信息进行存储
                    if (getDynInfos().size()>=20) {
                        FileDataManager.saveDynInfos(dynInfos, Vessel.this.getIdNumber());
                        System.err.println("Store over, next clear dyninfo...");
                        getDynInfos().clear();
                    }
                    else{
                        System.out.println(Vessel.this.getDynInfos().size());
                        System.err.println("need more...");
                    }
                }
            }
        });
    }
    
    /***********************************************************************************/
    public void setStaticAttribute(String idNumber, String name, int navLength, int beam, char type,
            String imoNumber, String callNumber, String destination, String expTime){
        super.setStaticAttribute(idNumber, name, navLength, beam, type);
        
        this.imoNumber = imoNumber;
        this.callNumber = callNumber;
        String[] point = destination.split(",");
        Point2D des = new Point2D( Double.parseDouble(point[0]), Double.parseDouble(point[1]) );
        super.destination = des;
        this.expTime = expTime;
    }
    
}
