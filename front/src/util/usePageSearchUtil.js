import { useCallback, useEffect, useRef, useState } from 'react';
import { useRouter } from 'next/router';

export const useDelayed = () => {
  const timerRef = useRef(null);

  const delayedSearch = useCallback((callback, delay) => {
    if (timerRef.current) {
      clearTimeout(timerRef.current);
    }
    timerRef.current = setTimeout(callback, delay);
  }, []);

  useEffect(() => {
    return () => {
      if (timerRef.current) {
        clearTimeout(timerRef.current);
      }
    };
  }, []);

  return delayedSearch;
};

export function throttle(api, delay = 600) {
  let timer;
  return function () {
    if (!timer) {
      timer = setTimeout(() => {
        timer = null;
        api.apply(this, arguments);
      }, delay);
    }
  };
}

export const usePageSearchUtil = () => {
  const delayedSearch = useDelayed();

  const [keyword, setKeyword] = useState('');

  const handleKeywordChange = useCallback(
    (event) => {
      delayedSearch(() => {
        setKeyword(event.target.value);
        window.scrollTo({ top: 0, behavior: 'auto' });
      }, 500);
    },
    [delayedSearch],
  );

  return {
    keyword,
    setKeyword,
    handleKeywordChange,
  };
};
