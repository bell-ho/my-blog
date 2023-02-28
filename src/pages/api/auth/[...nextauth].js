import NextAuth from 'next-auth';
import KakaoProvider from 'next-auth/providers/kakao';
import GoogleProvider from 'next-auth/providers/google';
import NaverProvider from 'next-auth/providers/naver';
import { axios } from '@/util/axios';
const nextAuthOptions = (req, res) => {
  return {
    secret: process.env.NEXT_PUBLIC_NEXTAUTH_SECRET,
    providers: [
      KakaoProvider({
        clientId: process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID,
        clientSecret: process.env.NEXT_PUBLIC_KAKAO_CLIENT_SECRET,
      }),
      GoogleProvider({
        clientId: process.env.NEXT_PUBLIC_GOOGLE_CLIENT_ID,
        clientSecret: process.env.NEXT_PUBLIC_GOOGLE_CLIENT_SECRET,
      }),
      NaverProvider({
        clientId: process.env.NEXT_PUBLIC_NAVER_CLIENT_ID,
        clientSecret: process.env.NEXT_PUBLIC_NAVER_CLIENT_SECRET,
      }),
    ],
    session: {
      jwt: true,
    },
    callbacks: {
      async signIn(user, account, metadata) {
        const provider = user.account.provider;

        const nickName = await axios
          .get(`https://nickname.hwanmoo.kr`, {
            params: {
              format: 'json',
              max_length: 7,
            },
          })
          .then((data) => data.data.words[0]);

        const params = {
          name: user.user?.name ?? '',
          nickName,
          uniqueKey: user.user?.id,
          email: user.user?.email ?? '',
          provider,
        };

        try {
          const existUser = await axios
            .get(`/api/v1/auth/validation-user/${params.uniqueKey}`)
            .then((response) => {
              res.setHeader('Set-Cookie', response.headers['set-cookie']);
              return response.data.data;
            });

          if (!existUser) {
            await axios.post(`/api/v1/auth/signup`, params).then((response) => {
              res.setHeader('Set-Cookie', response.headers['set-cookie']);
            });
          }
        } catch (e) {
          console.error(e);
        }
        return true;
      },
      async jwt({ token, account, user }) {
        if (account) {
          token.accessToken = account.access_token;
          token.id = user?.id;
        }
        return token;
      },
      async session({ session, token, user }) {
        session.user.id = token.id;
        return session;
      },
      async redirect({ url, baseUrl }) {
        return baseUrl;
      },
    },
  };
};

const authHandler = (req, res) => {
  return NextAuth(req, res, nextAuthOptions(req, res));
};

export default authHandler;
