FROM nginx:alpine

# Xoa config mac dinh
RUN rm /etc/nginx/conf.d/default.conf

# Copy file frontend vao nginx
COPY . /usr/share/nginx/html

# Copy config nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf
