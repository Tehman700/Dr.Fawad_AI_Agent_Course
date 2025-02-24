package com.azure;

import java.util.HashMap;
import java.util.Map;

class StringSimilarity {
    public static void main(String[] args) {
        StringSimilarity st = new StringSimilarity();
        String str1 = "LabAssignment";
        String str2 = "5-Lab Assignment";

        // Check similarity
        boolean isSimilar = st.areStringsSimilar(str1, str2, 2, 0.8); // Using bigrams and 0.8 as threshold
        System.out.println("Are the strings similar? " + isSimilar);
    }

    public boolean areStringsSimilar(String str1, String str2, int nGramSize, double threshold) {
        // Step 1: Preprocess strings
        String normalizedStr1 = preprocessString(str1);
        String normalizedStr2 = preprocessString(str2);


        // Step 2: Extract numerical values
        Integer num1 = extractNumber(normalizedStr1);
        Integer num2 = extractNumber(normalizedStr2);

        // Step 3: Handle cases with numerical values
        if (num1 != null && num2 != null && !num1.equals(num2)) {
            return false; // Numerical values differ
        }

        // Step 4: Calculate cosine similarity
        double similarity = cosineSimilarity(getFrequencyMap(normalizedStr1, nGramSize), getFrequencyMap(normalizedStr2, nGramSize));
        // Step 5: Compare with threshold
        return similarity >= threshold;
    }

    public String preprocessString(String str) {
        // Convert to lowercase and remove special characters (except whitespace)
        return str.toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", " ").trim();
    }

    public Integer extractNumber(String str) {
        // Extract the first numerical value from the string
        String number = str.replaceAll("[^0-9]", " ").trim();
        if (!number.isEmpty()) {
            try {
                return Integer.parseInt(number.split("\\s+")[0]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public Map<String, Integer> getFrequencyMap(String str, int nGramSize) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        // Generate n-grams at the character level (including whitespace)
        for (int i = 0; i <= str.length() - nGramSize; i++) {
            String nGram = str.substring(i, i + nGramSize);
            frequencyMap.put(nGram, frequencyMap.getOrDefault(nGram, 0) + 1);
        }

        return frequencyMap;
    }

    public double cosineSimilarity(Map<String, Integer> vec1, Map<String, Integer> vec2) {
        double dotProduct = 0.0, norm1 = 0.0, norm2 = 0.0;

        // Calculate dot product and norm for vec1
        for (String key : vec1.keySet()) {
            dotProduct += vec1.getOrDefault(key, 0) * vec2.getOrDefault(key, 0);
            norm1 += Math.pow(vec1.get(key), 2);
        }

        // Calculate norm for vec2
        for (int value : vec2.values()) {
            norm2 += Math.pow(value, 2);
        }

        // Handle cases where norm is zero
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
