/** @type {import('next').NextConfig} */

const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  images: {
    domains: [process.env.NEXT_PUBLIC_IMAGES_DOMAIN],
  },
  compiler: {
    emotion: true,
  },
};

module.exports = nextConfig;
