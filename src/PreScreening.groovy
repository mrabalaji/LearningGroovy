/**
 * Created by mrabalaji on 1/16/17.
 */
@SuppressWarnings("GroovyAssignabilityCheck")
class PreScreening {

    static def run(Map attrs, int reviewDetailsPerPage) {
        def groupedByFeatures = new HashMap()
        def each = attrs.reviewsList.each { Map<String, String> review ->
            String feature = review.get("feature")
            def reviews = groupedByFeatures.get(feature)
            if (reviews == null) {
                reviews = new ArrayList()
            }
            reviews.add(review)
            groupedByFeatures.putAt feature, reviews
        }
        List groupedAndPaged = new ArrayList()
        def each1 = groupedByFeatures.entrySet().each { Map attrs1 ->
            def page = new ArrayList()
            int pageCounter = 0
            attrs1.entry.getValue().each { Map<String, Object> review ->
                page.add(review)
                if (reviewDetailsPerPage == page.size()) {
                    def reviewDetail = new HashMap()
                    reviewDetail.put "feature", attrs1.entry.getKey()
                    reviewDetail.put "page", pageCounter
                    reviewDetail.put "totalRecords", attrs1.entry.getValue().size()
                    reviewDetail.put "reviews", page
                    groupedAndPaged.add reviewDetail
                    pageCounter++
                    page = new ArrayList()
                }
            }
        }

        return groupedAndPaged
    }

    static void main(String [] args) {
        def preScreening = new PreScreening()
        List<Map> input = new ArrayList();

        def reviewOne = new HashMap()
        reviewOne.putAt "reviewId", "2874274"
        reviewOne.putAt "feature", "can use this product"
        input.add reviewOne
        println "Done by Balaji"

        def reviewTwo = new HashMap()
        reviewTwo.putAt "reviewId", "3535533"
        reviewTwo.putAt "feature", "can use this product"
        input.add reviewTwo

        def reviewThree = new HashMap()
        reviewThree.putAt "reviewId", "5839589"
        reviewThree.putAt "feature", "can not use this product"
        input.add reviewThree

        def output = preScreening.run(1, input)

        def expectedOutput = new ArrayList()
        def reviewPageOne = new HashMap()
        reviewPageOne.putAt "feature", "can use this product"
        reviewPageOne.putAt "page", 0
        reviewPageOne.put "totalRecords", 2

        def reviewListOne = new ArrayList()
        reviewListOne.add reviewOne
        reviewPageOne.putAt "reviews", reviewListOne
        expectedOutput.add reviewPageOne

        def reviewPageTwo = new HashMap()
        reviewPageTwo.putAt "feature", "can use this product"
        reviewPageTwo.putAt "page", 1
        reviewPageTwo.putAt "totalRecords", 2

        def reviewListTwo = new ArrayList()
        reviewListTwo.add reviewTwo
        reviewPageTwo.put "reviews", reviewListTwo
        expectedOutput.add reviewPageTwo

        def reviewPageThree = new HashMap()
        reviewPageThree.putAt "feature", "can not use this product"
        reviewPageThree.put "page", 0
        reviewPageThree.putAt "totalRecords", 1

        def reviewListThree = new ArrayList()
        reviewListThree.add reviewThree
        reviewPageThree.putAt "reviews", reviewListThree
        expectedOutput.add reviewPageThree

        if (expectedOutput == output) {
        } else {
            throw new AssertionError("Expected: $expectedOutput" + " is not equals to Actual: " + output)
        }

    }
}




