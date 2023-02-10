import '@/styles/globals.css';
import Layout from '@/components/layout/Layout';
import { useRef } from 'react';
import { queryClient } from '@/react-query/queryClient';
import { Hydrate, QueryClientProvider } from '@tanstack/react-query';

export default function App({ Component, pageProps }) {
  const queryClientRef = useRef();
  if (!queryClientRef.current) {
    queryClientRef.current = queryClient;
  }

  return (
    <QueryClientProvider client={queryClientRef.current}>
      <Hydrate state={pageProps.dehydratedState}>
        <Layout>
          <Component {...pageProps} />
        </Layout>
      </Hydrate>
    </QueryClientProvider>
  );
}
