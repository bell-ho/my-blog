/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  images: {
    domains: [process.env.IMAGES_DOMAIN],
  },
  compiler: {
    emotion: true,
  },
};

module.exports = nextConfig;
