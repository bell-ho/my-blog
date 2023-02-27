import { useCallback, useEffect, useRef, useState } from 'react';
import { useRouter } from 'next/router';

const useDelayedSearch = () => {
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

export const usePageSearchUtil = ({ defaultSize = 10, pageParam = 0 }) => {
  const router = useRouter();
  const { asPath, query } = router;

  const delayedSearch = useDelayedSearch();

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(defaultSize);
  const [keyword, setKeyword] = useState('');

  useEffect(() => {
    setPage(pageParam);
  }, [pageParam]);

  const handleKeywordChange = useCallback(
    (event) => {
      delayedSearch(() => {
        setPage(0);
        setKeyword(event.target.value);

        // 검색시 1 페이지
        const [baseUrl, queryStr] = asPath.split('?');
        const queryParams = new URLSearchParams(queryStr);
        queryParams.set('page', 1);
        const newUrl = `${baseUrl}?${queryParams.toString()}`;
        router.push({ pathname: newUrl, query }, newUrl);
      }, 500);
    },
    [asPath, delayedSearch, query, router],
  );

  return {
    page,
    setPage,
    size,
    keyword,
    handleKeywordChange,
  };
};

export const pageChangeFn = (asPath, page) => {
  const [baseUrl, queryStr] = asPath.split('?');
  const queryParams = new URLSearchParams(queryStr);
  const newPage = page + 1;
  queryParams.set('page', newPage);
  return `${baseUrl}?${queryParams.toString()}`;
};
