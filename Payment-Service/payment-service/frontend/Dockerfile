FROM nginx:1.27-alpine

# Static UI + simple reverse-proxy to backend containers to avoid CORS issues.

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY public/ /usr/share/nginx/html/
