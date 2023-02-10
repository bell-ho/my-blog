import React from 'react';
import { signIn, useSession, signOut } from 'next-auth/react';
const Login = () => {
  const { data: session } = useSession();

  if (session) {
    return (
      <div>
        {session.user?.name}님 <br />
        <button onClick={() => signOut()}>로그아웃</button>
      </div>
    );
  }
  return (
    <div>
      <button onClick={() => signIn('kakao')}>로그인</button>
    </div>
  );
};
export default Login;
