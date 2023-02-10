import { QueryClient } from '@tanstack/react-query';

const queryErrorHandler = (error) => {
  const content = error instanceof Error ? error?.response?.data : 'error';
  console.log(error);
  console.log(content);
};

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      onError: queryErrorHandler,
      staleTime: 300000,
      cacheTime: 600000,
      refetchOnMount: false, // 데이터가 stale 상태일 경우 마운트 시 마다 refetch를 실행하는 옵션
      refetchOnReconnect: false, // 데이터가 stale 상태일 경우 재 연결 될 때 refetch를 실행하는 옵션
      refetchOnWindowFocus: false, // 데이터가 stale 상태일 경우 윈도우 포커싱 될 때 마다 refetch를 실행하는 옵션
    },
    mutations: {
      onError: queryErrorHandler,
    },
  },
});

// export function generateQueryClient() {
//   return new QueryClient({
//     defaultOptions: {
//       queries: {
//         onError: queryErrorHandler,
//         staleTime: 600000,
//         cacheTime: 900000,
//         refetchOnMount: false,
//         refetchOnReconnect: false,
//         refetchOnWindowFocus: false,
//       },
//       mutations: {
//         onError: queryErrorHandler,
//       },
//     },
//   });
// }

// export const queryClient = generateQueryClient();
