package wrappers;
//Класс, отвечающий за сохранение введенных команд

import java.util.PriorityQueue;
import java.util.Queue;

public class History {
    private Queue<String> scriptHistory = new PriorityQueue<>();


    private String command6 = "";
    private String command5 = "";
    private String command4 = "";
    private String command3 = "";
    private String command2 = "";
    private String command1 = "";

    private int kol_command = 0;

    public History() {

    }

    //Добавление элемента со смещением индекса вверх
    public void add(String command) {
        if (!command.equals("")) {
            this.kol_command++;
            if (this.kol_command > 6) this.kol_command = 6;
            switch (this.kol_command) {
                case 1:
                    this.command1 = command;
                    break;
                case 2:
                    this.command2 = this.command1;
                    this.command1 = command;
                    break;
                case 3:
                    this.command3 = this.command2;
                    this.command2 = this.command1;
                    this.command1 = command;
                    break;
                case 4:
                    this.command4 = this.command3;
                    this.command3 = this.command2;
                    this.command2 = this.command1;
                    this.command1 = command;
                    break;
                case 5:
                    this.command5 = this.command4;
                    this.command4 = this.command3;
                    this.command3 = this.command2;
                    this.command2 = this.command1;
                    this.command1 = command;
                    break;
                default:
                    this.command6 = this.command5;
                    this.command5 = this.command4;
                    this.command4 = this.command3;
                    this.command3 = this.command2;
                    this.command2 = this.command1;
                    this.command1 = command;
            }
        }
    }

    public boolean addScriptPath(String scriptPath){
        if(this.scriptHistory.contains(scriptPath)){
            return false;
        }
        if(this.scriptHistory.offer(scriptPath)){
            return true;
        }   else {
            System.out.println("Произошла ошибка");
            return false;
        }
    }

    public void popScriptPath(String scriptPath){
        if(!this.scriptHistory.contains(scriptPath)) return;
        for(String string : this.scriptHistory){
            if(string.equals(scriptPath)) {
                this.scriptHistory.remove(string);
                return;
            }
        }
    }

    public void printHistory() {
        if (!this.command6.equals("")) System.out.println("6. " + this.command6);
        if (!this.command5.equals("")) System.out.println("5. " + this.command5);
        if (!this.command4.equals("")) System.out.println("4. " + this.command4);
        if (!this.command3.equals("")) System.out.println("3. " + this.command3);
        if (!this.command2.equals("")) System.out.println("2. " + this.command2);
        if (!this.command1.equals("")) System.out.println("1. " + this.command1);
    }

}
