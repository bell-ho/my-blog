import styled from '@emotion/styled';

const Checkbox = ({ children, disabled, checked, onChange }) => {
  return (
    <Wrapper>
      <label>
        <input
          type="checkbox"
          disabled={disabled}
          checked={checked}
          onChange={({ target: { checked } }) => onChange(checked)}
        />
        {children}
      </label>
    </Wrapper>
  );
};

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  font-size: var(--size-4);
  gap: 10px;
  height: 40px;

  input {
    width: auto;
    margin-right: 10px;
  }
`;

export default Checkbox;
