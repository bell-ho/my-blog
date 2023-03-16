export { default } from 'next-auth/middleware';
import { getToken } from 'next-auth/jwt';
import { NextResponse } from 'next/server';

const secret = process.env.NEXT_PUBLIC_NEXTAUTH_SECRET;
export async function middleware(req) {
  const session = await getToken({ req, secret, raw: true });

  if (!session) {
    return NextResponse.redirect(new URL('/login', req.url));
  }
}

export const config = {
  matcher: ['/'],
};
