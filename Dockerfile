FROM openjdk:23-jdk-bullseye

RUN apt-get update && apt-get install -y \
    x11-apps \
    xvfb \
    libxext6 \
    libxrender1 \
    libxtst6


# Set the working directory
WORKDIR /app

# Copy your JAR file into the container
COPY out/artifacts/Sir_Fawad_agent_jar/Sir_Fawad_agent.jar /app/Sir_Fawad_agent.jar

# Expose necessary ports (if required)
EXPOSE 8080

# Start Xvfb for headless GUI support and run the Java application
CMD ["sh", "-c", "Xvfb :99 -screen 0 1920x1080x24 & export DISPLAY=:99 && java -jar /app/Sir_Fawad_agent.jar"]
