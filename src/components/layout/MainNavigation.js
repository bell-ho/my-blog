import React from 'react';
import Logo from '@/components/layout/Logo';
import Link from 'next/link';

import classes from './main-navigation.module.css';
import styled from '@emotion/styled';

const MainNavigation = ({ handleKeywordChange }) => {
  return (
    <header className={classes.header}>
      <ul>
        <li>
          <Link href={'/'}>
            <Logo />
          </Link>
        </li>
        <li>
          <Search>
            <input
              type="text"
              placeholder="해시태그를 검색하세요."
              onChange={handleKeywordChange}
            />
          </Search>
        </li>
      </ul>
    </header>
  );
};

const Search = styled.div`
  display: flex;
  align-items: center;
  max-width: 20rem;
  height: 40px;
  background-color: #f7f7f7;
  border-radius: 15px;
  padding: 0 10px;

  input {
    border: none;
    outline: none;
    flex: 1;
    margin-left: 10px;
    font-size: 14px;
    color: #555;
    background-color: transparent;
  }
`;
export default MainNavigation;
