export { default } from 'next-auth/middleware';
import { getSession } from 'next-auth/react';
import { NextResponse } from 'next/server';

export async function middleware(req) {
  const requestForNextAuth = {
    headers: {
      cookie: req.headers.get('cookie'),
    },
  };

  const session = await getSession({ req: requestForNextAuth });

  if (!session) {
    return NextResponse.redirect(new URL('/login', req.url));
  }
}

export const config = { matcher: ['/', '/contact', '/posts/:path*'] };
