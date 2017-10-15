FROM node:8.2.1
ARG configuration

# Create app directory
RUN mkdir -p /usr/opt/filter-web
WORKDIR /usr/opt/filter-web

# Bundle app source
COPY node_modules node_modules
COPY lib lib
COPY conf conf
COPY dist dist

EXPOSE 8081
CMD ["node", "--max_old_space_size=2048", "lib/filtering-web.js"]