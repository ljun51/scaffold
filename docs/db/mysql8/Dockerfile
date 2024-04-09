FROM mysql:8.0.34

LABEL maintainer="mysql8"

# Set Timezone
ARG TZ=Asia/Shanghai
ENV TZ ${TZ}
ENV MYSQL_ROOT_PASSWORD ljun51
ENV LANG zh_CN.utf8
RUN pwd \
  && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
  && echo $TZ > /etc/timezone \
  && chown -R mysql:root /var/lib/mysql/ \
  && true

EXPOSE 3306