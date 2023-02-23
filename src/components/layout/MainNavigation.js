import React from 'react';
import Logo from '@/components/layout/Logo';
import Link from 'next/link';

import classes from './main-navigation.module.css';

const MainNavigation = () => {
  return (
    <header className={classes.header}>
      <Link href={'/'}>
        <Logo />
      </Link>
      <nav>
        <ul>
          <li>
            <Link href="/posts">Posts</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};
export default MainNavigation;
