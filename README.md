# Course Automated Analyzer

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Introduction
The **Course Automated Analyzer** is a Java-based application designed to automate the analysis and organization of course materials. It helps users filter, categorize, and process files and folders related to quizzes, assignments, labs, mid-term exams, and final exams. The application integrates with OpenAI's API to intelligently filter and categorize files based on their content and metadata.

This project is particularly useful for students, educators, and administrators who deal with large volumes of course materials and need an efficient way to organize and analyze them.

## Features
- **Directory Parsing**: Recursively scans directories to identify folders and files.
- **Folder Filtering**: Filters folders based on their content (e.g., only folders containing files).
- **File Filtering**: Uses OpenAI's API to intelligently filter files related to quizzes, assignments, labs, mid-term exams, and final exams.
- **Auto-Closing Popups**: Displays informative popups that automatically close after 2 seconds.
- **File Deletion Handling**: Manages file deletions in a vector database and ensures proper cleanup.
- **Report Generation**: Generates analysis reports in both Markdown and plain text formats.
- **PDF Conversion**: Converts Markdown reports to PDF for easy sharing and printing.

## Technologies Used
- **Java**: The core programming language used for the application.
- **Swing**: For building the graphical user interface (GUI).
- **Apache PDFBox**: For merging and converting PDF files.
- **OpenAI API**: For intelligent file filtering and categorization.
- **Dotenv**: For managing environment variables (e.g., API keys).
- **JSON**: For parsing API responses and handling data.
- **Maven**: For dependency management and project building.

## Setup and Installation
### Prerequisites
- Java Development Kit (JDK) 11 or higher.
- Maven for dependency management.
- OpenAI API key (stored in a `.env` file).

### Steps
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/course-automated-analyzer.git
   cd course-automated-analyzer
   ```

2. **Set Up Environment Variables:**
    - Create a `.env` file in the root directory.
    - Add your OpenAI API key:
      ```ini
      API_KEY=your_openai_api_key_here
      ```

3. **Build the Project:**
   ```bash
   mvn clean install
   ```

4. **Run the Application:**
   ```bash
   java -jar target/course-automated-analyzer.jar
   ```

## Usage
### Launch the Application
- Run the application using the command provided above.
- A GUI window will appear with options to select a directory and start the analysis.

### Select a Directory
- Click the **"Select Directory"** button to choose the root directory containing your course materials.

### Start Analysis
- Click the **"Analyze"** button to begin processing the files and folders.
- The application will display popups with progress updates and results.

### View Reports
- After the analysis is complete, the application will generate two reports:
    - `Analysis Report.md`: A Markdown-formatted report.
    - `Analysis Report.txt`: A plain text version of the report.
- The Markdown report will also be converted to a PDF for easy sharing.

## Project Structure
```
course-automated-analyzer/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.azure/
│   │   │   │   ├── GUI.java          # Main GUI class
│   │   │   │   ├── Main.java         # Entry point and core logic
│   │   │   │   ├── DirectoryParser.java # Directory parsing and filtering logic
│   │   │   │   ├── OpenAISetup.java  # OpenAI API integration
│   │   │   │   ├── OpenAIFileUpload.java # File upload logic
│   │   │   │   ├── VectorStoreFileDeleter.java # File deletion logic
│   │   │   │   └── MarkdownToPdfConverter.java # PDF conversion logic
│   │   └── resources/                # Resource files (e.g., .env)
├── target/                           # Compiled output
├── .env                              # Environment variables
├── pom.xml                           # Maven configuration
└── README.md                         # Project documentation
```

## Contributing
We welcome contributions to the project! If you'd like to contribute, please follow these steps:

### Fork the Repository
- Fork the repository to your GitHub account.

### Create a Branch
- Create a new branch for your feature or bug fix:
  ```bash
  git checkout -b feature/your-feature-name
  ```

### Make Changes
- Implement your changes and ensure the code is well-documented.

### Test Your Changes
- Run the application and ensure everything works as expected.

### Submit a Pull Request
- Push your changes to your forked repository and submit a pull request to the main repository.

## License
This project is licensed under the **MIT License**. See the `LICENSE` file for details.

## Acknowledgments
- **OpenAI**: For providing the API used for intelligent file filtering.
- **Apache PDFBox**: For enabling PDF conversion and merging.
- **Dotenv**: For simplifying environment variable management.
- **Java Swing**: For making it easy to build a cross-platform GUI.

For any questions or issues, please open an issue on the GitHub repository.

