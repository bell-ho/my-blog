import React, { Fragment } from 'react';
import MainNavigation from '@/components/layout/MainNavigation';

const Layout = ({ children }) => {
  return (
    <Fragment>
      <MainNavigation />
      <main>{children}</main>
    </Fragment>
  );
};

export default Layout;
