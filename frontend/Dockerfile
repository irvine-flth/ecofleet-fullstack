FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

FROM nginx:1.25-alpine
COPY --from=builder /app/dist/frontend/browser /usr/share/nginx/html
COPY nginx-conf.conf /etc/nginx/conf.d/default.conf
