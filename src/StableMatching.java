import java.util.LinkedList;
import java.util.Queue;

public class StableMatching {

    static int[][] hospital_pref = new int[][]{{3, 2, 1}, {1, 2, 3}, {1, 2, 3}};
    static int[][] student_pref = new int[][]{{3, 2, 1}, {2, 1, 3}, {3, 1, 2}};

    public static void main(String[] args) {
        String[] Match = new String[3];
        int[][] hospital_rank = new int[3][3];  //i输出学校,j输出学生
        int[][] student_rank = new int[3][3];  //i输出学生,j输出学校
        int[] student_match = new int[3]; //第i个学生配对的学校
        int[] hospital_match = new int[3];
        Queue<Integer> isNotMatched = new LinkedList<>(); //未配对队列
        //队列初始化
        isNotMatched.offer(1);
        isNotMatched.offer(2);
        isNotMatched.offer(3);
        //生成rank表
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                hospital_rank[i][hospital_pref[i][j] - 1] = j + 1;
                student_rank[i][student_pref[i][j] - 1] = j + 1;
            }
        }

        //医院pref表
        System.out.println("医院pref表：");
        for (int i = 0; i < 3; i++) {
            System.out.print("h" + (i + 1) + " prefer: ");
            for (int j = 0; j < 3; j++) {
                System.out.print((j + 1) + "=" + "s" + hospital_pref[i][j] + " ");
            }
            System.out.println();
        }
        //学生pref表
        System.out.println("学生pref表：");
        for (int i = 0; i < 3; i++) {
            System.out.print("s" + (i + 1) + " prefer: ");
            for (int j = 0; j < 3; j++) {
                System.out.print((j + 1) + "=" + "h" + student_pref[i][j] + " ");
            }
            System.out.println();
        }
        //医院rank表
        System.out.println("医院rank表：");
        for (int i = 0; i < 3; i++) {
            System.out.print("h" + (i + 1) + " rank: ");
            for (int j = 0; j < 3; j++) {
                System.out.print("s" + (j + 1) + "=" + hospital_rank[i][j] + " ");
            }
            System.out.println();
        }
        //学生rank表
        System.out.println("学生rank表：");
        for (int i = 0; i < 3; i++) {
            System.out.print("s" + (i + 1) + " rank: ");
            for (int j = 0; j < 3; j++) {
                System.out.print("h" + (j + 1) + "=" + student_rank[i][j] + " ");
            }
            System.out.println();
        }

        //StableMatching
        while (!isNotMatched.isEmpty()){
            for (int i = 0; i < 3; i++) {    //3个医院
                for (int j = 0; j < 3; j++) {   //3个学生
                    int s = hospital_pref[i][j]-1; //h未申请的第一个学生s
                    if (student_match[s]==0&&hospital_match[i]==0){ //如果学生s未配对
                        student_match[s]=i+1; //s和h配对
                        hospital_match[i]=s+1;
                        isNotMatched.poll(); //队列中移除已配对医院

                        System.out.println("h"+(i+1)+"和"+"s"+(s+1)+"配对");
                        System.out.print("未配对医院:");
                        for(int k : isNotMatched) {
                            System.out.print("h"+k+" ");
                        }
                        System.out.println();

                    }else if (student_match[s]!=0&&hospital_match[i]==0){    //如果学生s已配对,医院h未配对
                        int h_cur = student_match[s]-1;
                        if(student_rank[s][i]<student_rank[s][h_cur]){ //如果第i个学校优先度比已配对的学校高
                            student_match[s]=i+1;
                            hospital_match[i]=s+1;
                            hospital_match[h_cur]=0; //清除原来的配对
                            isNotMatched.poll(); //队列中移除已配对医院
                            isNotMatched.offer(h_cur+1);

                            System.out.print("h"+(i+1)+"和"+"s"+(s+1)+"配对, ");
                            System.out.println("移除h"+(h_cur+1)+"和s"+(s+1)+"的配对");
                            System.out.print("配对后未配对医院:");
                            for(int k : isNotMatched) {
                                System.out.print("h"+k+" ");
                            }
                            System.out.println();
                        }
                    }else if(student_match[s]!=0&&hospital_match[i]!=0){  //如果学生s和医院h均已配对
                        int h_cur = student_match[s]-1;
                        int s_cur = hospital_match[i]-1;
                        if(student_rank[s][i]<student_rank[s][h_cur]&&hospital_rank[i][s]<hospital_rank[i][s_cur]){ //如果第i个学校优先度比已配对的学校高
                            student_match[s]=i+1;
                            hospital_match[i]=s+1;
                            student_match[s_cur]=0; //清除原来的配对
                            hospital_match[h_cur]=0;
                            isNotMatched.poll(); //队列中移除已配对医院
                            isNotMatched.offer(h_cur+1);

                            System.out.print("h"+(i+1)+"和"+"s"+(s+1)+"配对, ");
                            System.out.println("移除h"+(h_cur+1)+"和s"+(s+1)+"的配对");
                            System.out.print("配对后未配对医院:");
                            for(int k : isNotMatched) {
                                System.out.print("h"+k+" ");
                            }
                            System.out.println();
                        }
                    }
                }
            }
        }
        //输出结果
        for (int i = 0; i < 3; i++) {
            Match[i]= "h"+(i+1)+"-"+"s"+hospital_match[i];
        }
        System.out.println("配对结果:");
        for(int i=0;i<3;i++){
            System.out.print(Match[i]+" ");
        }
    }
}