package com.ilstress;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrigramCount {
    public static void main(String[] args) {

        File dir = new File("books");

        Map<String, Integer> countMap = new HashMap<>();

        File[] files = dir.listFiles();
        if(files == null){

            System.out.println("找不到books文件夹！检查路径");
            System.out.println("当前工作目录：" + System.getProperty("user.dir"));
            return;
        }

        for(File f : files){
            if(!f.isFile()) continue;
            if(!f.getName().toLowerCase().endsWith(".txt")) continue;

            System.out.println("正在读取：" + f.getName());
            try(BufferedReader br = new BufferedReader(new FileReader(f))){
                String line;
                while((line = br.readLine()) != null){
                    String pure = line.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    if(pure.length() < 3) continue;
                    for(int i = 0; i <= pure.length()-3; i++){
                        String tri = pure.substring(i,i+3);
                        countMap.put(tri, countMap.getOrDefault(tri,0)+1);
                    }
                }
            }catch (IOException e){
                System.err.println("读取失败 "+f.getName());
            }
        }

        List<Map.Entry<String,Integer>> list = new ArrayList<>(countMap.entrySet());
        list.sort((o1,o2)->{
            int cmp = Integer.compare(o2.getValue(), o1.getValue());
            if(cmp !=0) return cmp;
            return o1.getKey().compareTo(o2.getKey());
        });

        System.out.println("\n====频率 Top10====");
        int limit = Math.min(10, list.size());
        for(int i=0;i<limit;i++){
            var item = list.get(i);
            System.out.printf("%-6s %d%n", item.getKey(), item.getValue());
        }
    }
}
