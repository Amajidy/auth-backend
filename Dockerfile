# ====================================================================
# مرحله ۱: مرحله ساخت (Build Stage)
# این مرحله کد را کامپایل و بسته بندی می‌کند، اما در ایمیج نهایی وجود نخواهد داشت.
# ====================================================================
FROM maven:3.9.5-eclipse-temurin-21 AS builder

# تنظیم دایرکتوری کاری
WORKDIR /app

# کپی کردن فایل pom.xml و دانلود وابستگی‌ها (برای استفاده از کش داکر)
COPY pom.xml .
RUN mvn dependency:go-offline

# کپی کردن سورس کد
COPY src ./src

# ساخت فایل JAR قابل اجرا (Executable JAR)
# ما از دستور 'package' برای ایجاد jar استفاده می‌کنیم
RUN mvn package -DskipTests

# ====================================================================
# مرحله ۲: مرحله اجرا (Runtime Stage)
# از یک ایمیج سبک‌تر حاوی فقط JVM برای اجرای برنامه استفاده می‌کنیم.
# ====================================================================
FROM eclipse-temurin:21-jre-alpine

# تنظیم متغیر محیطی برای فعالسازی JMX/بررسی‌های سلامت (Health Checks)
ENV JAVA_TOOL_OPTIONS "-Xms128m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

# در این مرحله، فقط فایل JAR ساخته شده در مرحله builder را کپی می‌کنیم.
# نام فایل JAR را با توجه به ساختار پروژه خود (مثلاً: kyc-backend-0.0.1-SNAPSHOT.jar) جایگزین کنید.
ARG JAR_FILE=target/kyc-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/${JAR_FILE} app.jar

# تعریف پورتی که برنامه روی آن اجرا می‌شود (مثلاً 8080)
#EXPOSE 8080

# دستور اجرا (Entrypoint)
# دستور جاوا برای اجرای فایل JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]