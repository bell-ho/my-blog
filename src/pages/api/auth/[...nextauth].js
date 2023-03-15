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
        clientId: process.env.KAKAO_CLIENT_ID,
        clientSecret: process.env.KAKAO_CLIENT_SECRET,
      }),
      GoogleProvider({
        clientId: process.env.GOOGLE_CLIENT_ID,
        clientSecret: process.env.GOOGLE_CLIENT_SECRET,
      }),
      NaverProvider({
        clientId: process.env.NAVER_CLIENT_ID,
        clientSecret: process.env.NAVER_CLIENT_SECRET,
      }),
    ],
    session: {
      jwt: true,
    },
    pages: {
      signIn: '/login',
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
              return response.data.data;
            });

          if (!existUser) {
            await axios.post(`/api/v1/auth/signup`, params).then((response) => {});
          }
        } catch (e) {
          console.error(e);
        }

        return true;
      },
      async jwt({ token, account, user }) {
        if (account) {
          const existUser = await axios
            .get(`/api/v1/auth/validation-user/${user?.id}`)
            .then((response) => {
              return response.data.data.member;
            });

          token.role = existUser?.role;
          token.accessToken = existUser?.token;
          token.id = user?.id;

          // token.accessToken = account.access_token;
        }
        return token;
      },
      async session({ session, token, user }) {
        session.accessToken = token.accessToken;
        session.user.id = token.id;
        session.user.role = token.role;
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
