FROM node:8.2.1
ARG configuration

# Create app directory
RUN mkdir -p /usr/opt/vizceral
WORKDIR /usr/opt/vizceral

# Bundle app source
COPY node_modules node_modules
COPY lib lib
COPY conf conf
COPY dist dist

EXPOSE 8080
CMD ["node", "--max_old_space_size=2048", "lib/filtering-web.js"]