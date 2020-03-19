import {TestBed} from '@angular/core/testing';
import {DateFormatterService} from './dateFormatter.service';

let service: DateFormatterService;

describe('DateFormatterService', () => {
  beforeEach(() => {
    service = TestBed.get(DateFormatterService);
  });

  it('should format the date',  () => {
    const date: Date = new Date('2020-01-01T00:00:00');
    expect(service.formatDate(date)).toEqual('2020-01-01');
  });
});
