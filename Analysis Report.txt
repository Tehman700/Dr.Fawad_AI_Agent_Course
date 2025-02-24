# Quiz 1.docx

It appears that there are no indexed search results from the uploaded files. Please specify how you would like to evaluate or analyze the content, or provide specific details about the questions and solutions you want to focus on. You may also consider re-uploading the files if they were not properly processed.

# ---------------------------------------------------------------------




# Quiz 2.docx

### Question Analysis

**Question 1: Concatenating Strings (CLO-2, C3)**
- **Taxonomy Level**: C3 (Applying).
- **Analysis**: The question requires students to apply knowledge to create a program for string concatenation.
  
**Question 2: Searching Substrings (CLO-2, C3)**
- **Taxonomy Level**: C3 (Applying).
- **Analysis**: Similar to question 1, this requires the application of knowledge to determine if a substring exists within the main string.

**Question 3: Splitting Strings (CLO-2, C4)**
- **Taxonomy Level**: C4 (Analyzing).
- **Analysis**: This question moves to a higher level by requiring students to analyze a sentence and print individual words, which aligns with the specified taxonomy level.

### Suggestions for Improvement:
1. For **Question 1** and **Question 2**, to elevate them to a C4 level, you might consider asking students to analyze the input strings for certain characteristics before performing concatenation or search operations. Here are suggestions:

   - **Improved Question 1**: "Create a program that prompts the user to enter two strings and analyzes the length and character composition of each string before concatenating them with a space in between. Provide a summary of the analysis."
   - **Example**: "Create a program to prompt the user for two strings, analyze their lengths, check for the presence of digits or special characters, and then concatenate them with a space in between."
  
   - **Improved Question 2**: "Develop a program that takes two strings as input. Before checking if the second string is present within the first, analyze and report on the frequency of each character in both strings."
   - **Example**: "Write a program that accepts two strings, analyzes their character frequencies, and then checks if the second string is present within the first. Print both the analysis and the result of the substring search."

2. **Question 3** is already aligned with the appropriate taxonomy level.

---

### Solution Analysis

**Solution 1 for Question 1 - Concatenating Strings:**
- **Rating**: 8/10
- **Key Elements**: Correctly concatenates strings and uses a class for structure.
- **Suggestions for Improvement**: To improve, include string analysis as proposed in the suggested question.

**Solution 2 for Question 2 - Searching Substrings:**
- **Rating**: 8/10
- **Key Elements**: Correctly checks for substring presence.
- **Suggestions for Improvement**: Enhance functionality to analyze character frequency as suggested.

**Solution 3 for Question 3 - Splitting Strings:**
- **Rating**: 9/10
- **Key Elements**: Efficiently splits strings and iterates through the results.
- **Suggestions for Improvement**: Could include additional validation for input strings (e.g., checking for empty strings).

### Overall Recommendations:
- Align all questions to the appropriate levels of Bloom's Taxonomy by revising ones that are at a lower level.
- Solutions should be updated to reflect these improvements, enhancing their complexity and ensuring they meet higher levels of application or analysis.

# ---------------------------------------------------------------------




# Quiz 3.docx

### Question Analysis

**Question:**
Create a java program to input a paragraph from the user and count the words “The” and “is” ignoring the case and write the count in a file.  
**CLO:** CLO-3  
**Taxonomy Level Specified:** C5 (Create)

1. **Identified Level:**
   The question requires students to develop a program, which falls under the "Create" level of Bloom's Taxonomy (level C5). This aligns with the specified taxonomy level.

### Solution Analysis

**Solution:**  
```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class WordCounter {
    public int countWord(String paragraph, String word) {
        String lowerCaseParagraph = paragraph.toLowerCase();
        String lowerCaseWord = word.toLowerCase();
        int count = 0;
        int index = 0;

        while ((index = lowerCaseParagraph.indexOf(lowerCaseWord, index)) != -1) {
            count++;
            index += lowerCaseWord.length();
        }

        return count;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a paragraph:");
        String paragraph = scanner.nextLine();
        WordCounter wordCounter = new WordCounter();
        int countThe = wordCounter.countWord(paragraph, "the");
        int countIs = wordCounter.countWord(paragraph, "is");

        System.out.println("Count of 'The': " + countThe);
        System.out.println("Count of 'is': " + countIs);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("word_count.txt"))) {
            writer.write("Count of 'The': " + countThe + "\n");
            writer.write("Count of 'is': " + countIs + "\n");
            System.out.println("Word counts have been written to word_count.txt.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
```

1. **Rating: 9/10**  
   - The solution correctly implements the requirements of the question, demonstrating an understanding of user input, string manipulation, file writing, and case insensitivity.
   
2. **Key Elements:**
   - Utilizes a `WordCounter` class to encapsulate word counting logic.
   - The program effectively counts occurrences of the specified words ignoring case.
   - Implements file writing to capture the output.

3. **Suggestions for Improvement:**
   - **Error Handling:** The code could benefit from additional error handling when reading input from the user, such as checking for empty strings or handling potential exceptions.
   - **Scalability:** To make the solution more robust, consider allowing the user to input multiple words for counts rather than hard-coding "the" and "is".

### Summary:
The question and solution align correctly with the specified Bloom's Taxonomy level C5. The solution is effectively solved with minor improvements suggested for robust error handling and scalability.

# ---------------------------------------------------------------------




