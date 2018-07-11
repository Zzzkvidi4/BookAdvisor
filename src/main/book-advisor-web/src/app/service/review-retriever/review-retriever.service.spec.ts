import { TestBed, inject } from '@angular/core/testing';

import { ReviewRetrieverService } from './review-retriever.service';

describe('ReviewRetrieverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReviewRetrieverService]
    });
  });

  it('should be created', inject([ReviewRetrieverService], (service: ReviewRetrieverService) => {
    expect(service).toBeTruthy();
  }));
});
