import React, { useEffect, useState } from 'react';
import { css } from '@emotion/react';

const ScrollToTopButton = () => {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      if (window.pageYOffset > 100) {
        setVisible(true);
      } else {
        setVisible(false);
      }
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  const handleClick = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  return (
    <button css={buttonStyle} style={{ display: visible ? 'block' : 'none' }} onClick={handleClick}>
      Top
    </button>
  );
};
const buttonStyle = css`
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 50px;
  height: 50px;
  background-color: #333;
  color: #fff;
  border: none;
  border-radius: 50%;
  cursor: pointer;

  z-index: 999;

  &:hover {
    background-color: #555;
  }
`;
export default ScrollToTopButton;
