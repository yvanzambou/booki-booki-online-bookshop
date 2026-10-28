# ---- Build-Stage: Vite-Build ----
FROM node:22-alpine AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .
# API-Basis-URL zur Build-Zeit: nginx leitet /booki-booki an das Backend weiter
ARG VITE_API_URL=/booki-booki
ENV VITE_API_URL=$VITE_API_URL
RUN npm run build

# ---- Runtime-Stage: nginx liefert die statischen Dateien ----
FROM nginx:1.27-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
