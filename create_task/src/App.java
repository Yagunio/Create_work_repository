import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    final public static String с_rep_name     = "D:/Downloads";
    final public static String c_correct_name = "TASK-.[0-9]*";
    final public static String c_first_dir    = "first";
    final public static String c_second_dir   = "second";
    final public static String c_father_dir   = "father";
    final public static String c_mother_dir   = "mother";

    // Кодировка считываемого, чтобы русские буквы корректно отображались
    final public static String l_encoding = System.getProperty("console.encoding", "cp866");
    // Класс, для считывания вводимого текста с консоли
    final public static Scanner c_in  = new Scanner (System.in, l_encoding);

    public static void main(String[] args) throws Exception 
    {
        // Чистим консоль, чтобы не мусорилась
        clean_cmd();

        String l_task_name;
        String l_task_dir;
        String l_type_dir;
        String l_file_dir;
        String l_file_name;
        String l_message;

        do {
            System.out.println("Введите номер задачи (TASK-<номер>) или 0, чтобы выйти: ");
            l_task_name = c_in.nextLine().toUpperCase();
            if (l_task_name.equals("0"))
                return;
            else if(!Pattern.matches(c_correct_name, l_task_name)) {
                System.out.println("Номер задачи не соответствует шаблону TASK-<номер>");
                continue;
            }
        
            l_task_dir = с_rep_name + "/" + l_task_name;
            create_dir(l_task_dir);

            do {
                System.out.println("Введите тип наката (" + c_first_dir + ", " + c_second_dir + ") или 0, чтобы выйти: ");
                l_message = c_in.nextLine();
                if (l_message.equals("0"))
                    return;
                else if (l_message == null || (!l_message.equals(c_first_dir) && !l_message.equals(c_second_dir))){
                    System.out.println("Неверно введен тип наката!");
                    continue;
                }

                l_type_dir = l_task_dir + "/" + l_message;
                create_dir(l_type_dir);
                
                do{
                    System.out.println("Введите схему ("+ c_father_dir +", " + c_mother_dir +") или 0, чтобы выйти: ");
                    l_message = c_in.nextLine();
                    if (l_message.equals("0"))
                        return;
                    else if (l_message == null || (!l_message.equals(c_father_dir) && !l_message.equals(c_mother_dir))){
                        System.out.println("Неверно введена схема наката!");
                        continue;
                    }
                    l_file_name = l_message + ".sql";
                    l_file_dir  = l_type_dir + "/" + l_message;
                    create_dir(l_file_dir);

                    File newFile = new File(l_file_dir + "/" + l_file_name);
                    if (!newFile.createNewFile()){
                        System.out.println("Не удалось создать файл <" + l_file_dir + "/" + l_file_name + ">");
                        continue;
                    }
                    FileOutputStream l_fos = new FileOutputStream(l_file_dir + "/" + l_file_name);
                    byte[] l_buffer = "@@".getBytes();
                    l_fos.write(l_buffer, 0, l_buffer.length);
                    l_fos.close();

                    if (!is_contunue("Создать еще одну папку схемы? (Yes, No): ")){
                        break;
                    }
                } while(true); // Схема наката

                if (!is_contunue("Создать еще одну папку типа наката? (Yes, No): ")){
                    break;
                }
            } while(true); // Тип наката
            System.out.println("Директория для задачи " + l_task_name + " создана!");
        } while(true); // Задача
    }

    /*
    * Очистка консоли
    */
    static void clean_cmd(){
        String ESC = "\033[";
        System.out.println(ESC + "2J"); 
    }

    static void create_dir(String p_name) throws Exception {
        File l_new_dir = new File(p_name);
        if (l_new_dir.isDirectory()) {
            System.out.println("Папка <" + p_name + "> уже существует");
        }
        else {
            if (!l_new_dir.mkdir()) 
                throw new Exception("Не удалось создать папку <" + p_name + ">");
            else {
                System.out.println("Создали папку задачи");
            }
        }
    }

    static boolean is_contunue(String p_question){
        System.out.println(p_question);
        String l_message = c_in.nextLine();
        if (l_message.equals("Yes"))
            return true;
        else
            return false;
    }

}

