import {Employee} from './employee';

describe('Employee', () => {
  it('should create an instance', () => {
    expect(new Employee('', '', new Date() , '', '', false)).toBeTruthy();
  });
});
