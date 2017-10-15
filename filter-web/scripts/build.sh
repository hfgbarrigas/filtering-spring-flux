#!/bin/bash

rm -rf node_modules
rm -rf dist
npm install --production
npm run build-web-prod