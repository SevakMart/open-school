const { createProxyMiddleware } = require('http-proxy-middleware');

const proxy = createProxyMiddleware({
  target: 'http://localhost:5000',
});

module.exports = (app) => {
  app.use(['/api'], proxy);
};
