import React from 'react';
import { getSession, signIn, useSession } from 'next-auth/react';
import styled from '@emotion/styled';
import Image from 'next/image';

const Login = () => {
  const { data: session } = useSession();

  // if (session) {
  //   return (
  //     <div>
  //       {session.user?.name}님 <br />
  //       <button onClick={() => signOut()}>로그아웃</button>
  //     </div>
  //   );
  // }

  return (
    <Wrapper>
      <Contents>
        <h1>판도라</h1>
        <Image width={300} height={300} src={'/images/site/jh.png'} alt={'good'} />
        <LoginButton onClick={() => signIn('kakao')}>
          <Image
            width={300}
            height={45}
            layout="intrinsic"
            src="/images/login/kakao_login_large_wide.png"
            alt="카카오 로그인 버튼"
          />
        </LoginButton>
        <LoginButton onClick={() => signIn('google')}>
          <Image
            width={300}
            height={45}
            layout="intrinsic"
            src="/images/login/google_signin_light_normal_web@2x.png"
            alt="구글 로그인 버튼"
          />
        </LoginButton>
        <LoginButton onClick={() => signIn('naver')}>
          <Image
            width={300}
            height={45}
            layout="intrinsic"
            src="/images/login/btnG_naver.png"
            alt="네이버 로그인 버튼"
          />
        </LoginButton>
      </Contents>
    </Wrapper>
  );
};

const Wrapper = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const Contents = styled.div`
  background-color: dodgerblue;
  padding: 2rem;
  height: auto;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;

  h1 {
    font-weight: bold;
  }
`;

const LoginButton = styled.button`
  background-color: transparent;
  border: none;
  cursor: pointer;
`;

export async function getServerSideProps(context) {
  const session = await getSession({ req: context.req });

  if (session) {
    return {
      redirect: {
        destination: '/',
        permanent: false,
      },
    };
  }

  return {
    props: {
      session,
    },
  };
}

export default Login;
