import NextAuth from 'next-auth';
import KakaoProvider from 'next-auth/providers/kakao';
import GoogleProvider from 'next-auth/providers/google';
import NaverProvider from 'next-auth/providers/naver';
import { axios } from '@/util/axios';

export default NextAuth({
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
        id: user.user?.id,
        name: user.user?.name ?? '',
        nickName,
        email: user.user?.email ?? '',
        provider,
      };

      console.log(params);

      return true;
    },
  },
});
