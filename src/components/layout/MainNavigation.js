import React, { Fragment, useState } from 'react';
import Logo from '@/components/layout/Logo';
import Link from 'next/link';

import classes from './main-navigation.module.css';
import styled from '@emotion/styled';
import { signOut } from 'next-auth/react';

const MainNavigation = ({ handleKeywordChange, setKeyword }) => {
  const [isSearchOpen, setIsSearchOpen] = useState(false);

  const handleSearchClick = () => {
    setIsSearchOpen(true);
  };

  const handleSearchClose = () => {
    setIsSearchOpen(false);
    setKeyword('');
  };

  const handleSignOut = async () => {
    await signOut();
  };

  return (
    <Wrapper>
      <LinkWrapper isSearchOpen={isSearchOpen}>
        <Link href={'/'}>
          <Logo />
        </Link>
      </LinkWrapper>
      <IconWrapper>
        {isSearchOpen ? (
          <SearchWrapper>
            <Search>
              <input
                type="text"
                placeholder="해시태그를 검색하세요."
                onChange={handleKeywordChange}
              />
              <CloseButton onClick={handleSearchClose}>
                <i className="material-icons">close</i>
              </CloseButton>
            </Search>
          </SearchWrapper>
        ) : (
          <Fragment>
            <IconButton onClick={handleSearchClick}>
              <i className="material-icons">search</i>
            </IconButton>
            <IconButton onClick={handleSignOut}>
              <i className="material-icons">logout</i>
            </IconButton>
          </Fragment>
        )}
      </IconWrapper>
    </Wrapper>
  );
};
const Wrapper = styled.header`
  position: fixed;
  top: 0;
  width: 100%;
  height: 5rem;
  background-color: var(--color-grey-900);
  display: flex;
  justify-content: space-between;
  align-items: center;
  z-index: 9999;
  padding: 0 5%;

  @media screen and (max-width: 768px) {
    justify-content: flex-end;
  }
`;

const LinkWrapper = styled.div`
  flex: 1;

  @media screen and (max-width: 768px) {
    display: ${({ isSearchOpen }) => (isSearchOpen ? 'none' : 'flex')};
  }
`;

const IconWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;

  @media screen and (max-width: 768px) {
    justify-content: flex-end;
  }
`;

const SearchWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 20px;
`;

const Search = styled.div`
  display: flex;
  align-items: center;
  width: 20rem;
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

const IconButton = styled.button`
  background: none;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: white;
`;

const CloseButton = styled(IconButton)`
  color: #593e3e;
`;
export default MainNavigation;
