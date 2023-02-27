import { getToken } from 'next-auth/jwt';
import { getSession } from 'next-auth/react';
import { NextResponse } from 'next/server';

const secret = process.env.NEXT_PUBLIC_NEXTAUTH_SECRET;
export async function middleware(req) {
  const requestForNextAuth = {
    headers: {
      cookie: req.headers.get('cookie'),
    },
  };
  const session = await getSession({ req: requestForNextAuth });
  // const session = await getToken({ req, secret, raw: true });

  if (!session) {
    return NextResponse.redirect(new URL('/login', req.url));
  }
}

export const config = {
  matcher: ['/'],
};
