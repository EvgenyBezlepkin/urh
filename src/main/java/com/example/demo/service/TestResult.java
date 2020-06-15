package com.example.demo.service;

import com.example.demo.domain.IntermediateResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestResult {

    private IntermediateResult interRes;

    public TestResult() {
    }

    public void setInterRes(IntermediateResult interRes) {
        this.interRes = interRes;
    }

    public String getResult(String lang) {
        int[] res1 = new int[]{interRes.getTOV(), interRes.getTEV(), interRes.getLPV(), interRes.getOFV(), interRes.getTAV()};
        int[] res2 = new int[]{interRes.getTOS(), interRes.getTES(), interRes.getLPS(), interRes.getOFS(), interRes.getTAS()};
        String[] res3 = new String[]{"Theorist-researcher", "Technician-executor", "Leader-teacher", "Social activist-philanthropist", "Creator-artist"};
        String[] res3Ru = new String[]{"Теоретик-исследователь", "Техник-исполнитель", "Лидер-преподаватель", "Общественник-филантроп", "Творец-артист"};
        String out1 = "", out2 = "", out3 = "", out4 = "", out5 = "";
        String[] out = new String[]{out1, out2, out3, out4, out5};

        if ("en".equals(lang)) {
            for (int i = 0; i < 5; i++) {
                if (res1[i] < 25) {
                    out[i] = res3[i] + ": inside the sphere: " + Math.round(res1[i]) + "% (low level). Share of all spheres: " + Math.round(res2[i]) + "%. ";
                }
                if (res1[i] >= 25 & res1[i] < 50) {
                    out[i] = res3[i] + ": inside the sphere: " + Math.round(res1[i]) + "% (average level). Share of all spheres: " + Math.round(res2[i]) + "%. ";
                }
                if (res1[i] >= 50 & res1[i] < 75) {
                    out[i] = res3[i] + ": inside the sphere: " + Math.round(res1[i]) + "% (above average level). Share of all spheres: " + Math.round(res2[i]) + "%. ";
                }
                if (res1[i] >= 75) {
                    out[i] = res3[i] + ": inside the sphere: " + Math.round(res1[i]) + "% (high level). Share of all spheres: " + Math.round(res2[i]) + "%. ";
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                if (res1[i] < 25) {
                    out[i] = res3Ru[i] + ": внутри сферы от максимального возможного уровня: " + Math.round(res1[i]) + "% (низкий уровень). Среди всех сфер профиль выражен на: " + Math.round(res2[i]) + "%. ";
                }
                if (res1[i] >= 25 & res1[i] < 50) {
                    out[i] = res3Ru[i] + ": внутри сферы от максимального возможного уровня: " + Math.round(res1[i]) + "% (средний уровень). Среди всех сфер профиль выражен на: " + Math.round(res2[i]) + "%. ";
                }
                if (res1[i] >= 50 & res1[i] < 75) {
                    out[i] = res3Ru[i] + ": внутри сферы от максимального возможного уровня: " + Math.round(res1[i]) + "% (уровень выше среднего). Среди всех сфер профиль выражен на: " + Math.round(res2[i]) + "%. ";
                }
                if (res1[i] >= 75) {
                    out[i] = res3Ru[i] + ": внутри сферы от максимального возможного уровня: " + Math.round(res1[i]) + "% (высокий уровень). Среди всех сфер профиль выражен на: " + Math.round(res2[i]) + "%. ";
                }
            }
        }

        StringBuilder resOut = new StringBuilder();
        resOut.append("<ul>");
        for (int i = 0; i < 5; i++) {
            resOut.append("<li>");
            resOut.append(out[i]);
            resOut.append("</li>");
            resOut.append("\n");
        }
        resOut.append("<br>");

        // внутри всех сфер
        Map<Integer, Integer> first = new TreeMap<>();
        first.put(0, interRes.getTOS());
        first.put(1, interRes.getTES());
        first.put(2, interRes.getLPS());
        first.put(3, interRes.getOFS());
        first.put(4, interRes.getTAS());

        first = this.sortByValue(first);

        System.out.println("Внутри всех сфер");
        first.forEach((k, v) -> System.out.print(k + " : " + v + " "));
        System.out.println();

        // внутри своей сферы
        Map<Integer, Integer> second = new TreeMap<>();
        second.put(0, interRes.getTOV());
        second.put(1, interRes.getTEV());
        second.put(2, interRes.getLPV());
        second.put(3, interRes.getOFV());
        second.put(4, interRes.getTAV());
        second = this.sortByValue(second);

        second.forEach((k, v) -> System.out.print(k + " : " + v + " "));

        ArrayList<Integer> firstSorted = new ArrayList(first.values());
        ArrayList<Integer> firstKey = new ArrayList<>(first.keySet());
        ArrayList<Integer> secondKey = new ArrayList(second.keySet());
        int howFirst = 1;
        for (int i = 4; i > 2; i--) {
            if (firstSorted.get(4).equals(0)){
                howFirst = 0;
                break;
            }
            if (!firstSorted.get(i).equals(0)) {
                if (firstSorted.get(i).equals(firstSorted.get(i - 1))) {
                    howFirst++;
                }
            }
        }
        if (!firstKey.get(4).equals(secondKey.get(4))) {
            howFirst++;
        }

        String outout = "";
        int j = 4;
        ArrayList<Integer> arrInt = new ArrayList<>(first.keySet());


        if ("en".equals(lang)) {
            for (int i = 0; i < howFirst; i++) {
                if (second.get(arrInt.get(j)) < 25) {
                    outout = "The most pronounced trait " + res3[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% of all spheres), low level (" + Math.round(second.get(arrInt.get(j))) + "%).";
                }
                if (second.get(arrInt.get(j)) >= 25 & second.get(arrInt.get(j)) < 50) {
                    outout = "The most pronounced trait " + res3[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% of all spheres), average level (" + Math.round(second.get(arrInt.get(j))) + "%).";
                }
                if (second.get(arrInt.get(j)) >= 50 & second.get(arrInt.get(j)) < 75) {
                    System.out.println("here");
                    outout = "The most pronounced trait " + res3[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% of all spheres), above average level (" + Math.round(second.get(arrInt.get(j))) + "%).";
                }
                if (second.get(arrInt.get(j)) >= 75) {
                    outout = "The most pronounced trait " + res3[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% of all spheres), high level (" + Math.round(second.get(arrInt.get(j))) + "%).";
                }
                j--;
                //resOut.append("<li>");
                resOut.append(outout);
                //resOut.append("</li>");
                resOut.append("\n");
            }
        } else {
            for (int i = 0; i < howFirst; i++) {
                if (second.get(arrInt.get(j)) < 25) {
                    outout = "Наиболее выражена черта " + res3Ru[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% среди всех сфер), низкий уровень (" + Math.round(second.get(arrInt.get(j))) + "%).";
                }
                if (second.get(arrInt.get(j)) >= 25 & second.get(arrInt.get(j)) < 50) {
                    outout = "Наиболее выражена черта " + res3Ru[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% среди всех сфер), средний уровень (" + Math.round(second.get(arrInt.get(j))) + "%).";
                }
                if (second.get(arrInt.get(j)) >= 50 & second.get(arrInt.get(j)) < 75) {
                    System.out.println("here");
                    outout = "Наиболее выражена черта " + res3Ru[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% среди всех сфер), уровень выше среднего (" + Math.round(second.get(arrInt.get(j))) + "%).";
                }
                if (second.get(arrInt.get(j)) >= 75) {
                    outout = "Наиболее выражена черта " + res3Ru[arrInt.get(j)] + " (" + Math.round(first.get(arrInt.get(j))) + "% среди всех сфер), высокий уровень (" + Math.round(second.get(arrInt.get(j))) + "%).";
                }
                j--;
                //resOut.append("<li>");
                resOut.append(outout);
                //resOut.append("</li>");
                resOut.append("\n");
            }
        }

        resOut.append("</ul>");
        return resOut.toString();
    }


    private Map<Integer, Integer> sortByValue(Map<Integer, Integer> unsortMap) {
        List<Map.Entry<Integer, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        list.sort(Comparator.comparing(o -> (o.getValue())));
        Map<Integer, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

}
