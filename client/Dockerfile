# stage 1 - build
FROM node:14-alpine AS builder

WORKDIR /opt/web
COPY package.json ./
RUN npm install
ENV PATH="./node_modules/.bin:$PATH"
COPY . ./
ARG API_URL
ENV REACT_APP_API_URL=${API_URL}
RUN npm run build

# stage 2 — build production image
FROM nginx:1.17-alpine
RUN apk --no-cache add curl
RUN curl -L https://github.com/a8m/envsubst/releases/download/v1.1.0/envsubst-`uname -s`-`uname -m` -o envsubst && \
    chmod +x envsubst && \
    mv envsubst /usr/local/bin
COPY ./nginx.config /etc/nginx/nginx.template
CMD ["/bin/sh", "-c", "envsubst < /etc/nginx/nginx.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
COPY --from=builder /opt/web/build /usr/share/nginx/html